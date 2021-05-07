import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignInComponent } from '../../sign-in/sign-in.component';
import { SignUpClientComponent } from '../../sign-up/sign-up-client.component';
import { SignUpSupplierComponent } from '../../sign-up/sign-up-supplier.component';
import { HomeComponent } from '../../home/home.component';
import { ForgotPasswordComponent } from '../../forgot-password/forgot-password.component';
import { DashboardComponent } from '../../dashboard/dashboard.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'sign-in', component: SignInComponent },
  { path: 'sign-up/client', component: SignUpClientComponent },
  { path: 'sign-up/supplier', component: SignUpSupplierComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'dashboard', component: DashboardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [],
})
export class AppRoutingModule {}
