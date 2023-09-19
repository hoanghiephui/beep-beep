package di

import domain.usecase.IIdentityUseCase
import domain.usecase.ILoginUserUseCase
import domain.usecase.ILogoutUserUseCase
import domain.usecase.IManageLocationUseCase
import domain.usecase.IManageOrderUseCase
import domain.usecase.IdentityUseCase
import domain.usecase.LoginUserUseCase
import domain.usecase.LogoutUserUseCase
import domain.usecase.ManageLocationUseCase
import domain.usecase.ManageOrderUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val UseCaseModule = module {
    singleOf(::LoginUserUseCase) { bind<ILoginUserUseCase>() }
    singleOf(::LogoutUserUseCase) { bind<ILogoutUserUseCase>() }
    singleOf(::ManageOrderUseCase) { bind<IManageOrderUseCase>() }
    singleOf(::ManageLocationUseCase) { bind<IManageLocationUseCase>() }
    singleOf(::IdentityUseCase) { bind<IIdentityUseCase>() }
}