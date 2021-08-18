import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-type-button',
  templateUrl: './user-type-button.component.html',
  styleUrls: ['./user-type-button.component.scss'],
})
export class UserTypeButtonComponent implements OnInit {
  @Input() accountType!: string;
  @Input() routePath!: string;
  @Input() imagePath!: string;

  constructor() {}

  ngOnInit(): void {}
}
