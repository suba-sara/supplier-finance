import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './routing/app-routing.module';
import {AppComponent} from './app.component';
import {SignUpClientComponent} from './sign-up/sign-up-client.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {SignUpSupplierComponent} from './sign-up/sign-up-supplier.component';
import {TopNavbarComponent} from './top-navbar/top-navbar.component';
import {HomeComponent} from './home/home.component';

// import angular materials
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule} from '@angular/forms';

// import httpClient
import {HttpClientModule} from '@angular/common/http';

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
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
