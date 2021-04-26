import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {SignInComponent} from '../sign-in/sign-in.component';
import {SignUpClientComponent} from '../sign-up/sign-up-client.component';
import {SignUpSupplierComponent} from '../sign-up/sign-up-supplier.component';

const routes: Routes = [
  {path: 'sign-in', component: SignInComponent},
  {path: 'sign-up/client', component: SignUpClientComponent},
  {path: 'sign-up/supplier', component: SignUpSupplierComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  declarations: []
})
export class AppRoutingModule {
}
