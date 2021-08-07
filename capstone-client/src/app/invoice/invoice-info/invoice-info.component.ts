import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { ViewInvoicesService } from '../view-invoices/view-invoices.service';
import { InvoiceInfoService } from './invoice-info.service';


@Component({
  selector: 'app-invoice-info',
  templateUrl: './invoice-info.component.html',
  styleUrls: ['./invoice-info.component.scss']
})
export class InvoiceInfoComponent implements OnInit {
  numberOfInvoice:any;
  status:any;
  statusUploaded:any = [];
  statusAPPROVED:any = [];
  statusREJECTED:any = [];
  userType = localStorage.getItem('user_type');
  constructor(private invoiceInfoService:InvoiceInfoService, private router: Router,
    private route: ActivatedRoute,
    public viewInvoicesService: ViewInvoicesService,) { }

  ngOnInit(): void {
    this.getInvoiceInfo();

  }

getInvoiceInfo(){
  this.invoiceInfoService.getInvoiceDetails(this.userType).subscribe(res => {
    this.numberOfInvoice = res;
    this.status = this.numberOfInvoice.content;

    for(var s of this.status){
      if(s.status === "UPLOADED"){
        this.statusUploaded.push(s.status)
        console.log(this.statusUploaded.length);
      }
      if(s.status === "APPROVED"){
        this.statusAPPROVED.push(s.status)
        console.log(this.statusAPPROVED.length);
      }
      if(s.status === "REJECTED"){
        this.statusREJECTED.push(s.status)
        console.log(this.statusREJECTED.length);
      }
    }
    
    this.numberOfInvoice =this.numberOfInvoice.numberOfElements;
  })
}

 

}
