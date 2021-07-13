import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'invoiceNumberFormat',
})
export class InvoiceNumberFormatPipe implements PipeTransform {
  transform(value: number): string {
    return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ' ');
  }
}
