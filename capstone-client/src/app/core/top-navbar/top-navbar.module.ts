import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopNavbarComponent } from './top-navbar.component';
import { RouterModule } from '@angular/router';
import { NavLinkComponent } from './nav-link/nav-link.component';
import { BurgerMenuComponent } from './burger-menu/burger-menu.component';
import { SharedModule } from '../../shared/shared.module';
import { MatRippleModule } from '@angular/material/core';

@NgModule({
  declarations: [TopNavbarComponent, NavLinkComponent, BurgerMenuComponent],
  imports: [CommonModule, RouterModule, SharedModule, MatRippleModule],
  exports: [TopNavbarComponent],
})
export class TopNavbarModule {}
