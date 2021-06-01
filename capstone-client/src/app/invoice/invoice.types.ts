export type Invoice = {
  invoiceId: number;
  clientId: number;
  supplierId: number;
  invoiceNumber: number;
  invoiceDate: Date;
  amount: number;
  status: string;
  currencyType: string;
};
