import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopNavbarModule } from './top-navbar/top-navbar.module';
import { AppRoutingModule } from './routing/app-routing.module';

@NgModule({
  imports: [CommonModule, TopNavbarModule, AppRoutingModule],
  exports: [TopNavbarModule, AppRoutingModule],
})
export class CoreModule {}
