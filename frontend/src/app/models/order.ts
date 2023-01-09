export interface Order {
  id: number;
  autoId: number;
  description: string;
  acceptanceDate: Date;
  finishedDate: Date;
  dutyIds: Array<number>;
  productIds: Array<number>;
  status: string;
  totalPrice: number;
}
