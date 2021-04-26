import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './routing/app-routing.module';
import { AppComponent } from './app.component';
import { SignUpClientComponent } from './sign-up/sign-up-client.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {SignUpSupplierComponent} from './sign-up/sign-up-supplier.component';
import { TopNavbarComponent } from './top-navbar/top-navbar.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    SignInComponent,
    SignUpClientComponent,
    SignUpSupplierComponent,
    TopNavbarComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
