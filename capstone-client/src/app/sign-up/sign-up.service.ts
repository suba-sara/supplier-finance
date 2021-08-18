import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { UserDetails } from './user-data-form/user-data-form.component';
import { PersonalDetails } from './personal-data-form/personal-data-form.component';
import { Observable } from 'rxjs';
import { AccountDetails } from './bank-account-details-form/bank-account-details-form.component';

const { API_PATH } = environment;

type SignupBaseDto = {
  userId: string;
  password: string;
  name: string;
  address: string;
  email: string;
  phone: string;
  accountNumber: string;
  otp: string;
};

@Injectable({
  providedIn: 'root',
})
export class SignUpService {
  constructor(private http: HttpClient) {}

  checkUsername(username: string): Observable<{ valid: boolean }> {
    return this.http.post<{ valid: boolean }>(
      `${API_PATH}/users/check-username`,
      username
    );
  }

  checkEmail(email: string): Observable<{ valid: boolean }> {
    return this.http.post<{ valid: boolean }>(
      `${API_PATH}/users/check-email`,
      email
    );
  }

  signUpSupplier = (
    data: UserDetails & PersonalDetails & AccountDetails
  ): Observable<void> => {
    // transform data
    const dto: SignupBaseDto = {
      ...this._prepareDto(data),
    };

    return this.http
      .post(`${API_PATH}/sign-up/supplier`, dto)
      .pipe(map(() => {}));
  };

  signUpClient = (
    data: UserDetails & PersonalDetails & AccountDetails
  ): Observable<void> => {
    // transform data
    const dto: SignupBaseDto = this._prepareDto(data);
    return this.http
      .post(`${API_PATH}/sign-up/client`, dto)
      .pipe(map(() => {}));
  };

  _prepareAddress = ({
    addressLine1,
    city,
    state,
    province,
    country,
  }: {
    addressLine1: string;
    city: string;
    state: string;
    province: string;
    country: string;
  }) => `${addressLine1}, ${city}, ${state}, ${province}, ${country}`;

  _prepareDto = ({
    userId,
    password,
    firstName,
    lastName,
    addressLine1,
    city,
    province,
    state,
    country,
    email,
    phone,
    accountNumber,
    otp,
  }: UserDetails & PersonalDetails & AccountDetails) => ({
    userId,
    password,
    name: `${firstName} ${lastName}`,
    address: this._prepareAddress({
      addressLine1,
      city,
      state,
      province,
      country,
    }),
    email,
    phone,
    accountNumber,
    otp,
  });
}
