import {Component} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Order} from "../models/order";
import {Observable} from "rxjs";
import {Product} from "../models/product";
import {Duty} from "../models/duty";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent {
  private url = 'http://localhost:8080/orders';
  orders: Array<Order> = [];
  totalPrice?: number;

  reactiveForm = new FormGroup({
    id: new FormControl(0),
    autoId: new FormControl(0),
    description: new FormControl(''),
    acceptanceDate: new FormControl(''),
    finishedDate: new FormControl(''),
  })

  statusOrderForm = new FormGroup({
    id: new FormControl(0),
    paymentStatus: new FormControl('')
  })

  orderForm = new FormGroup({
    id: new FormControl(0),
    title: new FormControl(''),
    price: new FormControl(0),
  })

  dutyForm = new FormGroup({
    orderId: new FormControl(0),
    masterId: new FormControl(0),
    price: new FormControl(0),
  })

  orderPriceForm = new FormGroup({
    id: new FormControl(0),
    discount: new FormControl(0),
  })

  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  save() {
    let id = this.reactiveForm.controls.id.value;
    let autoId = this.reactiveForm.controls.autoId.value
    let description = this.reactiveForm.controls.description.value;
    let acceptanceDate = this.reactiveForm.controls.acceptanceDate.value;
    let finishedDate = this.reactiveForm.controls.finishedDate.value;
    if (!autoId || !description || !acceptanceDate || !finishedDate) {
      return;
    }
    if (!id) {
      return this.createOrder({autoId, description, acceptanceDate,
        finishedDate} as Order)
        .subscribe(order => this.orders.push(order));
    } else {
      return this.updateOrder({id, autoId, description, acceptanceDate,
        finishedDate} as Order)
        .subscribe(order => this.orders.push(order));
    }
  }

  updateOrder(order: Order): Observable<Order> {
    return this.http.put<Order>(this.url + '/' + order.id, order, this.httpOptions);
  }

  createOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.url, order, this.httpOptions);
  }

  updateStatus() {
    let id = this.statusOrderForm.controls.id.value;
    let paymentStatus = this.statusOrderForm.controls.paymentStatus.value;
    this.http.put<Order>(this.url + '/' + id + '/update-status?payment-status=' + paymentStatus, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  addProduct() {
    let id = this.orderForm.controls.id.value;
    let title = this.orderForm.controls.title.value;
    let price = this.orderForm.controls.price.value;
    if (!id || !title || !price) {
      return;
    }
    return this.http.post<Order>(this.url + '/' + id + '/products', {title, price} as Product, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  addDuty() {
    let id = this.dutyForm.controls.orderId.value;
    let masterId = this.dutyForm.controls.masterId.value;
    let price = this.dutyForm.controls.price.value;
    if (!id || !masterId || !price) {
      return;
    }
    return this.http.post<Order>(this.url + '/' + id + '/duties', {
      orderId: id, masterId, price} as Duty, this.httpOptions)
      .subscribe(order => this.orders.push(order));
  }

  getTotalPrice() {
    let id = this.orderPriceForm.controls.id.value;
    if (!id) {
      return;
    }
    this.http.get<number>(this.url + '/' + id + '/calculate-price')
      .subscribe(price => this.totalPrice = price);
  }
}
