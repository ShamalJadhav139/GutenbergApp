package com.etpl.newbase.utilities
import com.etpl.newbase.utilities.AppSettingsCore.getBoolean

import com.etpl.newbase.utilities.AppSettingsCore.getString
import com.etpl.newbase.utilities.AppSettingsCore.removePrefData
import com.etpl.newbase.utilities.AppSettingsCore.setBoolean

import com.etpl.newbase.utilities.AppSettingsCore.setString

class AppSharedPreferences{
    companion object {
        private val KEY_APP_IS_OPEN = "app_is_open"
        private val KEY_IS_LOGIN = "is_login"
        private val KEY_IS_PRO_COMPLETE = "is_pro_complete"
        private val KEY_FIREBASE_INSTANCE_ID = "firebase_id"
        private val KEY_USER_ID = "user_id"
        private val KEY_EMAIL_ID = "email_id"
        private val KEY_LOGIN_OBJ = "login_obj"
        private val KEY_PROFILE_OBJ = "profile_obj"
        private val KEY_NOTI_COUNT = "noti_count"
        private val KEY_USER_NAME = "user_name"
        private val BOOKING_ID = "booking_id"
        private val GOT_IT = "GOT_IT"
        private val KEY_LANGUAGE = "selected_language_for_translation"
        private val KEY_USER_DETAILS = "KEY_USER_DETAILS"
        private val USER_TYPE = "USER_TYPE"
        private val FIRST_TIME = "first_time"
        private val CITY_NAME = "city_name"
        private val CITY_ID = "city_id"
        private val CAT_ID = "cat_id"
        private val SUB_CAT_ID = "sub_cat_id"
        private val IsCitySelected = "isCitySelected"
        private val KEY_DEFAULT_ADDRESS_ID = "default_address_id"




        fun clearUserData() {
            removePrefData()
        }

        var appIsOpen: Boolean
            get() = getBoolean(KEY_APP_IS_OPEN, false)
            set(appIsOpen) {
                setBoolean(KEY_APP_IS_OPEN, appIsOpen)
            }

        var isLogin: Boolean
            get() = getBoolean(KEY_IS_LOGIN, false)
            set(value) {
                setBoolean(KEY_IS_LOGIN, value)
            }

        var isProfileComplete: Boolean
            get() = getBoolean(KEY_IS_PRO_COMPLETE, false)
            set(value) {
                setBoolean(KEY_IS_PRO_COMPLETE, value)
            }


        var firebaseId: String?
            get() = getString(KEY_FIREBASE_INSTANCE_ID, null!!)
            set(keyUserId) {
                if (keyUserId != null) {
                    setString(KEY_FIREBASE_INSTANCE_ID, keyUserId)
                }
            }

        var userId: String?
            get() = getString(KEY_USER_ID, "0")
            set(keyUserId) {
                if (keyUserId != null) {
                    setString(KEY_USER_ID, keyUserId)
                }
            }

        var cityName: String?
            get() = getString(CITY_NAME, "0")
            set(keyCityName) {
                if (keyCityName != null) {
                    setString(CITY_NAME, keyCityName)
                }
            }

        var cityId: String?
            get() = getString(CITY_ID, "")
            set(keyCityId) {
                if (keyCityId != null) {
                    setString(CITY_ID, keyCityId)
                }
            }

        var catId: String?
            get() = getString(CAT_ID, "0")
            set(keyCatId) {
                if (keyCatId != null) {
                    setString(CAT_ID, keyCatId)
                }
            }

        var subCatId: String?
            get() = getString(SUB_CAT_ID, "0")
            set(keySubCatId) {
                if (keySubCatId != null) {
                    setString(SUB_CAT_ID, keySubCatId)
                }
            }

        var keyUserName: String?
            get() = getString(KEY_USER_NAME, null!!)
            set(keyUserName) {
                if (keyUserName != null) {
                    setString(KEY_USER_NAME, keyUserName)
                }
            }

        var addressId: String
            get() = getString(KEY_DEFAULT_ADDRESS_ID, "") ?: ""
            set(value) {
                setString(KEY_DEFAULT_ADDRESS_ID, value)
            }
    }
}