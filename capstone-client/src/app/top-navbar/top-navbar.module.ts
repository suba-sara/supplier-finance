import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopNavbarComponent } from './top-navbar.component';
import { RouterModule } from '@angular/router';
import { NavLinkComponent } from './nav-link/nav-link.component';
import { LogoComponent } from './logo/logo.component';
import { BurgerMenuComponent } from './burger-menu/burger-menu.component';

@NgModule({
  declarations: [
    TopNavbarComponent,
    NavLinkComponent,
    LogoComponent,
    BurgerMenuComponent,
  ],
  imports: [CommonModule, RouterModule],
  exports: [TopNavbarComponent],
})
export class TopNavbarModule {}
