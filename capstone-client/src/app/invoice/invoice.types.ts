type Client = {
  clientId: string;
  name: string;
};

type Supplier = {
  supplierId: string;
  name: string;
};

export type Invoice = {
  invoiceId: number;
  client: Client;
  supplier: Supplier;
  invoiceNumber: number;
  invoiceDate: Date;
  amount: number;
  status: string;
  currencyType: string;
};
