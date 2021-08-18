import { Component, OnInit } from '@angular/core';
import { Dayjs } from 'dayjs';
import { ActivatedRoute, Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import {
  InvoiceFiltersOptional,
  ViewInvoicesService,
} from './view-invoices.service';
import { Sort } from '@angular/material/sort';
import { AppService } from '../../app.service';
import { getDisplayColumns } from '../util/getDisplayColumns';
import { AuthService, UserType } from '../../core/auth/auth.service';
import { InvoiceStatus } from '../invoice.types';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-view-invoices',
  templateUrl: './view-invoices.component.html',
  styleUrls: ['./view-invoices.component.scss'],
})
export class ViewInvoicesComponent implements OnInit {
  displayedColumns: string[] = getDisplayColumns(
    this.authService.user.value?.userType
  );

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    public viewInvoicesService: ViewInvoicesService,
    private appService: AppService,
    public authService: AuthService,
    public dialog: MatDialog
  ) {}

  get userType(): UserType | undefined {
    return this.authService.user.value?.userType;
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      // assign parameters to state
      const pageSize = params['pageSize'] ? params['pageSize'] : 10;
      const pageIndex = params['pageNumber'] ? params['pageNumber'] - 1 : 0;
      const invoiceNumber = params['invoiceNumber'];
      const supplierId = params['supplierId'];
      const clientId = params['clientId'];
      const dateFrom = params['dateFrom']
        ? new Dayjs(params['dateFrom']).startOf('date').toDate()
        : undefined;
      const dateTo = params['dateTo']
        ? new Dayjs(params['dateTo']).startOf('date').toDate()
        : undefined;
      const ageing = params['ageing'];
      const status = params['status'];
      const sortBy = params['sortBy'] || 'uploadedDate';
      const sortDirection = params['sortDirection'] || 'DESC';

      this.viewInvoicesService.$filters.next({
        pageSize,
        pageIndex,
        invoiceNumber,
        supplierId,
        clientId,
        dateFrom,
        dateTo,
        ageing,
        status,
        sortBy,
        sortDirection,
      });
    });

    this.appService.setPageTitle('View Invoices');
  }

  filterInputChange = (e: any) => {
    const inputName = e.target?.name;
    const value = e.target?.value;

    this._changeQuery({
      [inputName]: !!value ? value : undefined,
    });
  };

  handlePageChange = (e: PageEvent) => {
    this._changeQuery({
      pageSize: e.pageSize,
      pageIndex: e.pageIndex,
    });
  };

  _changeQuery = (filers: InvoiceFiltersOptional) => {
    const queryParams: any = {
      ...this.viewInvoicesService.$filters.value,
      ...filers,
    };

    // adjust page number
    queryParams.pageNumber = queryParams.pageIndex + 1;
    delete queryParams.pageIndex;

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams,
      queryParamsHandling: 'merge',
    });
  };

  handleSortChange(e: Sort): void {
    this._changeQuery({
      sortBy: e.active,
      sortDirection: e.direction.toUpperCase(),
      pageIndex: 0,
    });
  }

  setInvoiceStatus(status?: InvoiceStatus): void {
    this._changeQuery({
      status,
    });
  }

  deleteInvoice({
    invoiceId,
    invoiceNumber,
  }: {
    invoiceId: number;
    invoiceNumber: string;
  }): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '250px',
      data: {
        title: 'Are you sure you want to delete this invoice?',
        message: `invoice ${invoiceNumber} will be deleted`,
      },
    });

    dialogRef.afterClosed().subscribe((confirmed: boolean) => {
      if (confirmed) {
        this.viewInvoicesService.deleteInvoice(invoiceId);
      }
    });
  }
}
