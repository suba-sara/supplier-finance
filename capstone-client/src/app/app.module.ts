import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CoreModule } from './core/core.module';
import { CommonModule } from '@angular/common';
import { HomeModule } from './home/home.module';
import { SignInModule } from './sign-in/sign-in.module';
import { ForgotPasswordModule } from './forgot-password/forgot-password.module';
import { DashboardModule } from './dashboard/dashboard.module';
import { HttpClientModule } from '@angular/common/http';
import { SignUpModule } from './sign-up/sign-up.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    CoreModule,
    HttpClientModule,
    HomeModule,
    SignInModule,
    SignUpModule,
    ForgotPasswordModule,
    DashboardModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
