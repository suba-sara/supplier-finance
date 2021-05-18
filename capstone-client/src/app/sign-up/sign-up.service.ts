import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';
import { UserDetails } from './user-data-form/user-data-form.component';
import { PersonalDetails } from './personal-data-form/personal-data-form.component';
import { Observable } from 'rxjs';

const { API_PATH } = environment;

type SupplierSignUpDTO = {
  userId: string;
  password: string;
  name: string;
  address: string;
  email: string;
  phone: string;
  interestRate: number;
};

@Injectable({
  providedIn: 'root',
})
export class SignUpService {
  constructor(private http: HttpClient) {}

  signUpSupplier = ({
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
  }: UserDetails & PersonalDetails): Observable<void> => {
    // transform data
    const dto: SupplierSignUpDTO = {
      userId,
      password,
      name: `${firstName} ${lastName}`,
      address: `${addressLine1}, ${city}, ${state}, ${province}, ${country}`,
      email,
      phone,
      // adding default interest rate for now
      interestRate: 5.0,
    };

    return this.http
      .post(`${API_PATH}/sign-up/supplier`, dto)
      .pipe(map(() => {}));
  };
}
