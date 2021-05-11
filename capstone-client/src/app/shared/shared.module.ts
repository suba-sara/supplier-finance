import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LogoComponent } from './logo/logo.component';
import { RouterModule } from '@angular/router';
import { TitlePanelComponent } from './title-panel/title-panel.component';

@NgModule({
  declarations: [LogoComponent, TitlePanelComponent],
  imports: [CommonModule, RouterModule],
  exports: [LogoComponent, TitlePanelComponent],
})
export class SharedModule {}
