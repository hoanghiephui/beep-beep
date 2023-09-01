package org.thechance.api_gateway.data.localizedMessages.languages

import org.koin.core.annotation.Single

@Single
class SyrianArabicLocalizedMessages : LocalizedMessages {

    // region identity
    override val invalidRequestParameter: String = "بيانات الطلب مالا صحيحة"
    override val invalidAddressLocation: String = "مكان العنوان مالو صحيح"
    override val userAlreadyExist: String = "المستخدم موجود بالفعل"
    override val invalidInformation: String = "المعلومات مالا صحيحة"
    override val invalidFullName: String = "الإسم الكامل مالو صحيح"
    override val invalidUsername: String = "اسم المستخدم مالو صحيح"
    override val passwordCannotBeLessThan8Characters: String = "الرقم السري ما يقل عن ٨ حروف"
    override val usernameCannotBeBlank: String = "اسم المستخدم ما يقدر يكون فاضي"
    override val passwordCannotBeBlank: String = "الرقم السري ما يقدر يكون فاضي"
    override val invalidEmail: String = "الإيميل مالو صحيح"
    override val notFound: String = "ما لقيناه"
    override val invalidCredentials: String = "بيانات الاعتماد مالا صحيحة"
    override val userCreatedSuccessfully: String = "المستخدم صار عنا 🎉"
    override val unknownError: String = "خطأ مالو معروف `¯\\_(ツ)_/¯`"
    override val userNotFound: String = "المستخدم ما لقيناه"
    override val invalidPermission: String = "صلاحية مالا صحيحة"
    // endregion

    // region taxi
    override val taxiCreatedSuccessfully: String = "سيارة التاكسي صارت عنا 🎉"
    override val taxiUpdateSuccessfully: String = "سيارة التاكسي صارت محدثة 🎉"
    override val taxiDeleteSuccessfully: String = "سيارة التاكسي صارت محذوفة"
    // endregion

    //region restaurant
    override val restaurantNotFound: String = "عذرًا، ما لقينا هالمطعم"
    //endregion
}
