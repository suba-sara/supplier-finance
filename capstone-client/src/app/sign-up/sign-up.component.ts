import {Component, OnInit} from '@angular/core';
import {AccountType} from './user-type-button/user-type-button.component';

@Component({
    selector: 'app-sign-up',
    templateUrl: './sign-up.component.html',
    styleUrls: ['./sign-up.component.scss'],
})
export class SignUpComponent implements OnInit {
    client: AccountType;
    supplier: AccountType;

    constructor() {
        this.client = {
            accountName: 'Client',
            redirectPath: '/sign-up/client',
            imagePath: '../../assets/Images/Client.png',
            accountInfo: ['Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.']
        };

        this.supplier = {
            accountName: 'Supplier',
            redirectPath: '/sign-up/supplier',
            imagePath: '../../assets/Images/Supplier.png',
            accountInfo: ['Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.',
                'Lorem ipsum dolor sit amet, consectetur.']
        };
    }

    ngOnInit(): void {
    }
}
