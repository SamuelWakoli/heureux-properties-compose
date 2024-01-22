package com.heureux.admin.data

import android.content.Context
import com.heureux.admin.data.repositories.HeureuxInquiriesRepository
import com.heureux.admin.data.repositories.HeureuxPaymentsRepository
import com.heureux.admin.data.repositories.HeureuxProfileRepository
import com.heureux.admin.data.repositories.HeureuxPropertyRepository
import com.heureux.admin.data.repositories.HeureuxUserFeedbackRepository
import com.heureux.admin.data.repositories.HeureuxUsersRepository
import com.heureux.admin.data.repositories.InquiriesRepository
import com.heureux.admin.data.repositories.PaymentsRepository
import com.heureux.admin.data.repositories.ProfileRepository
import com.heureux.admin.data.repositories.PropertyRepository
import com.heureux.admin.data.repositories.UserFeedbackRepository
import com.heureux.admin.data.repositories.UsersRepository
import com.heureux.admin.data.sources.HeureuxInquiriesDataSource
import com.heureux.admin.data.sources.HeureuxPaymentDataSource
import com.heureux.admin.data.sources.HeureuxProfileDataSource
import com.heureux.admin.data.sources.HeureuxPropertyDataSource
import com.heureux.admin.data.sources.HeureuxUserFeedbackDataSource
import com.heureux.admin.data.sources.HeureuxUsersDataSource

/**
 * An interface that provides access to the repositories used by the application.
 */
interface AppContainer {
    /** The repository for profile data. */
    val profileRepository: ProfileRepository

    /** The repository for property data. */
    val propertyRepository: PropertyRepository

    /** The repository for inquiries data. */
    val inquiriesRepository: InquiriesRepository

    /** The repository for payments data. */
    val paymentsRepository: PaymentsRepository

    /** The repository for user feedback data. */
    val userFeedbackRepository: UserFeedbackRepository

    /** The repository for users data. */
    val usersRepository: UsersRepository
}

/**
 * An implementation of the AppContainer interface for the Heureux application.
 */
class HeureuxAppContainer(context: Context) : AppContainer {

    override val profileRepository: ProfileRepository =
        HeureuxProfileRepository(dataSource = HeureuxProfileDataSource())

    override val propertyRepository: PropertyRepository =
        HeureuxPropertyRepository(dataSource = HeureuxPropertyDataSource())

    override val inquiriesRepository: InquiriesRepository =
        HeureuxInquiriesRepository(dataSource = HeureuxInquiriesDataSource())

    override val paymentsRepository: PaymentsRepository =
        HeureuxPaymentsRepository(dataSource = HeureuxPaymentDataSource())

    override val userFeedbackRepository: UserFeedbackRepository =
        HeureuxUserFeedbackRepository(dataSource = HeureuxUserFeedbackDataSource())

    override val usersRepository: UsersRepository =
        HeureuxUsersRepository(dataSource = HeureuxUsersDataSource())
}
