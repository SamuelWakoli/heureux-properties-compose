package com.heureux.admin.ui.presentation.screens.inquiries_screens.property_inquiry

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Archive
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.types.InquiryItem
import com.heureux.admin.data.types.PaymentItem
import com.heureux.admin.ui.presentation.screens.inquiries_screens.InquiriesViewModel
import java.time.LocalDateTime

@Composable
fun PropertyInquiryListItem(
    inquiryItem: InquiryItem,
    viewModel: InquiriesViewModel
) {

    var showOptions by remember { mutableStateOf(false) }
    var showApproveDialog by remember { mutableStateOf(false) }

    val allProperties = viewModel.allProperties.collectAsState().value
    val allUsers = viewModel.allUsers.collectAsState().value
    val context = LocalContext.current

    ListItem(modifier = Modifier.widthIn(max = 600.dp), overlineContent = {
        Text(
            text = "12:30pm  11/02/2024",
            color = MaterialTheme.colorScheme.secondary,
        )
    }, headlineContent = {
        Text(
            text = "Property Name",
            color = MaterialTheme.colorScheme.primary,
        )
    }, supportingContent = {
        Text(text = buildAnnotatedString {
            append("Username: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("John Doe")
            }
        })

        Text(text = buildAnnotatedString {
            append("Property price: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("2,000,000")
            }
        })

        Text(text = buildAnnotatedString {
            append("Offer amount: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("1,800,000")
            }
        })

        Text(text = buildAnnotatedString {
            append("Preferred payment method: ")
            withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Mortgage")
            }
        })
    }, trailingContent = {
        IconButton(onClick = { showOptions = true }) {
            Icon(
                imageVector = if (showOptions) Icons.Outlined.Close else Icons.Outlined.MoreVert,
                contentDescription = if (showOptions) "Hide" else "Options"
            )
        }

        DropdownMenu(expanded = showOptions, onDismissRequest = { showOptions = false }) {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.DoneAll, contentDescription = null
                )
            }, text = { Text(text = "Approve") }, onClick = {
                showOptions = false
                showApproveDialog = true
            })
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Call, contentDescription = null
                )
            },
                text = { Text(text = "Call ${allUsers?.find { it.email == inquiryItem.senderId }?.name}") },
                onClick = {
                    showOptions = false
                    val phone = allUsers?.find { it.email == inquiryItem.senderId }?.phone
                    if (phone != null && phone != "null") {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phone")
                        }
                        try {
                            context.startActivity(intent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(
                                context, "No phone app found", Toast.LENGTH_LONG
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            context, "This user has not added phone number", Toast.LENGTH_SHORT
                        ).show()
                    }

                })

            if (!inquiryItem.archived) DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Archive, contentDescription = null
                )
            }, text = { Text(text = "Archive") }, onClick = {
                showOptions = false
                viewModel.updateArchivePropertyInquiry(
                    inquiryItem,
                    onSuccess = {
                        Toast.makeText(
                            context, "Successfully archived inquiry", Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = {
                        Toast.makeText(
                            context, "Failed to archive inquiry", Toast.LENGTH_LONG
                        ).show()
                    },
                )
            })
            Divider(
                modifier = Modifier.padding(4.dp)
            )
            DropdownMenuItem(leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Delete, contentDescription = null
                )
            }, text = { Text(text = "Delete") }, colors = MenuDefaults.itemColors(
                leadingIconColor = MaterialTheme.colorScheme.error,
                textColor = MaterialTheme.colorScheme.error,
            ), onClick = {
                showOptions = false
                viewModel.deletePropertyInquiry(inquiryItem, onSuccess = {
                    Toast.makeText(
                        context, "Successfully deleted inquiry", Toast.LENGTH_SHORT
                    ).show()
                }, onFailure = {
                    Toast.makeText(
                        context, "Failed to delete inquiry", Toast.LENGTH_LONG
                    ).show()
                })
            })
        }
    })


    if (showApproveDialog) {
        PropertyApprovalDialog(propertyName = allProperties?.find { it.id == inquiryItem.propertyId }?.name
            ?: "",
            buyerName = allUsers?.find { it.email == inquiryItem.senderId }?.name ?: "",
            price = inquiryItem.offerAmount,
            onDismissRequest = { showApproveDialog = false },
            onApprove = {
                viewModel.addPayment(
                    payment = PaymentItem(
                        paymentId = LocalDateTime.now().toString(),
                        propertyId = inquiryItem.propertyId,
                        userId = inquiryItem.senderId,
                        amount = "0",
                        agreedPrice = inquiryItem.offerAmount,
                        totalAmountPaid = "0",
                        owingAmount = inquiryItem.offerAmount,
                        paymentMethod = inquiryItem.preferredPaymentMethod,
                        time = LocalDateTime.now().toString(),
                        approvedBy = Firebase.auth.currentUser?.email ?: "",
                    ),
                    onSuccess = {
                        viewModel.deletePropertyInquiry(
                            inquiryItem,
                            onSuccess = {
                                Toast.makeText(
                                    context, "Successfully deleted inquiry", Toast.LENGTH_SHORT
                                ).show()
                            },
                            onFailure = {
                                Toast.makeText(
                                    context,
                                    "Failed to delete this approved inquiry",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                        Toast.makeText(
                            context, "Successfully approved inquiry", Toast.LENGTH_SHORT
                        ).show()
                    },
                    onFailure = {
                        Toast.makeText(
                            context, "Failed to approve inquiry", Toast.LENGTH_LONG
                        ).show()
                    }
                )
            })
    }
}