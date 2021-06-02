import { Component, OnInit } from '@angular/core';
import { UserDetails } from './user-data-form/user-data-form.component';
import { PersonalDetails } from './personal-data-form/personal-data-form.component';

@Component({
  selector: 'app-sign-up-client',
  templateUrl: './sign-up-client.component.html',
  styleUrls: ['./sign-up-client.component.scss'],
})
export class SignUpClientComponent implements OnInit {
  personalDetails?: PersonalDetails;
  userDetails?: UserDetails;
  pageNumber: number;

  constructor() {
    this.pageNumber = 1;
  }

  submitUserData = (value: UserDetails): void => {
    this.userDetails = value;
    this.goToNextPage();
  };
  submitPersonalData = (value: PersonalDetails): void => {
    this.personalDetails = value;
    this.goToNextPage();
  };

  ngOnInit(): void {}

  gotoPreviousPage = (): void => {
    if (this.pageNumber > 1) {
      this.pageNumber = this.pageNumber - 1;
    }
  };

  goToNextPage = (): void => {
    if (this.pageNumber < 3) {
      this.pageNumber = this.pageNumber + 1;
    }
  };
}
