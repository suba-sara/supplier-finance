import { Pipe, PipeTransform } from '@angular/core';
import * as dayjs from 'dayjs';

@Pipe({
  name: 'InvoiceAge',
})
export class InvoiceAgePipe implements PipeTransform {
  transform(date: any, ...args: any[]): any {
    const formatOutput = (value: number, unit: string) =>
      value === 1 ? `${value} ${unit}` : `${value} ${unit}s`;

    const today = dayjs();

    const diffYears = dayjs(today).diff(date, 'years');
    if (diffYears) {
      return formatOutput(diffYears, 'year');
    }

    const diffMonths = dayjs(today).diff(date, 'months');
    if (diffMonths) {
      return formatOutput(diffMonths, 'month');
    }

    const diffDays = dayjs(today).diff(date, 'days');
    return formatOutput(diffDays, 'day');
  }
}
