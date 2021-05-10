import {Component, Input, OnInit} from '@angular/core';

export type AccountType = {
    accountName: string,
    redirectPath: string,
    imagePath: string,
    accountInfo: string[]
};

@Component({
    selector: 'app-user-type-button',
    templateUrl: './user-type-button.component.html',
    styleUrls: ['./user-type-button.component.scss']
})
export class UserTypeButtonComponent implements OnInit {

    @Input() accountType?: AccountType;

    constructor() {
    }

    ngOnInit(): void {
    }

}
