import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../../home/home.component';
import { ForgotPasswordComponent } from '../../forgot-password/forgot-password.component';
import { DashboardComponent } from '../../dashboard/dashboard.component';
import { SignInComponent } from '../../sign-in/sign-in.component';
import { SignUpClientComponent } from '../../sign-up/sign-up-client.component';
import { SignUpSupplierComponent } from '../../sign-up/sign-up-supplier.component';
import { SignUpComponent } from '../../sign-up/sign-up.component';
import { AuthGuard } from '../auth/auth.guard';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['UNAUTHORIZED'],
    },
  },
  {
    path: 'sign-in',
    component: SignInComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['UNAUTHORIZED'],
    },
  },
  {
    path: 'sign-up',
    component: SignUpComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['UNAUTHORIZED'],
    },
  },
  {
    path: 'sign-up/client',
    component: SignUpClientComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['UNAUTHORIZED'],
    },
  },
  {
    path: 'sign-up/supplier',
    component: SignUpSupplierComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['UNAUTHORIZED'],
    },
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['UNAUTHORIZED'],
    },
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    data: {
      roles: ['SUPPLIER', 'CLIENT', 'BANK'],
    },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: [],
})
export class AppRoutingModule {}
