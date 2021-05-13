import { APP_INITIALIZER, NgModule } from '@angular/core';
import { TopNavbarModule } from './top-navbar/top-navbar.module';
import { AppRoutingModule } from './routing/app-routing.module';
import { appInitializer } from './app-initializer';
import { AuthService } from './auth/auth.service';
import { LeftNavbarModule } from './left-navbar/left-navbar.module';
import { AuthModule } from './auth/auth.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { JwtInterceptor } from './auth/jwt.interceptor';
import { UnauthorizedInterceptor } from './auth/unauthorized.interceptor';

@NgModule({
  providers: [
    {
      provide: APP_INITIALIZER,
      useFactory: appInitializer,
      multi: true,
      deps: [AuthService],
    },
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: UnauthorizedInterceptor,
      multi: true,
    },
  ],
  exports: [TopNavbarModule, LeftNavbarModule, AppRoutingModule, AuthModule],
})
export class CoreModule {}
