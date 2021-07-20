import { Pipe, PipeTransform } from '@angular/core';
import * as dayjs from 'dayjs';

@Pipe({
  name: 'DateFormat',
})
export class DateFormatPipe implements PipeTransform {
  transform(value: any, ...args: any[]): any {
    return dayjs(value).format('DD-MMM-YYYY');
  }
}
