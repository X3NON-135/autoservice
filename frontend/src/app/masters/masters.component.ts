import {Component} from '@angular/core';
import {Observable} from "rxjs";
import {Order} from "../models/order";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Master} from "../models/master";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-masters',
  templateUrl: './masters.component.html',
  styleUrls: ['./masters.component.css']
})
export class MastersComponent {
  private url = 'http://localhost:8080/masters';
  masters: Array<Master> = []
  orders: Array<Order> = []
  salary?: number;

  masterForm = new FormGroup({
    id: new FormControl(0),
    fullName: new FormControl(''),
  })

  masterIdForm = new FormGroup({
    id: new FormControl(0),
  })

  salaryForm = new FormGroup({
    masterId: new FormControl(0),
    orderId: new FormControl(0),
  })

  constructor(private http: HttpClient) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    })
  };

  toNumber(value: string) {
    return Number(value);
  }

  save() {
    let id = this.masterForm.controls.id.value;
    let fullName = this.masterForm.controls.fullName.value;
    if (!fullName) {
      return;
    }
    fullName = fullName.trim();
    if (!id) {
      this.createMaster({fullName} as Master)
        .subscribe(master => this.masters.push(master));
    } else {
      this.updateMaster({id, fullName} as Master)
        .subscribe(master => this.masters.push(master));
    }
  }

  private createMaster(master: Master): Observable<Master> {
    return this.http.post<Master>(this.url, master, this.httpOptions).pipe();
  }

  private updateMaster(master: Master): Observable<Master> {
    return this.http.put<Master>(this.url + '/' + master.id, master, this.httpOptions);
  }

  getOrder() {
    let id = this.masterIdForm.controls.id.value;
    if (!id) {
      return;
    }
    this.http.get<Array<Order>>(this.url + '/' + id + '/orders')
      .subscribe(orders => this.orders = orders);
  }

  getSalary() {
    let masterId = this.salaryForm.controls.masterId.value;
    let orderId = this.salaryForm.controls.orderId.value;
    if (!masterId) {
      return;
    }
    this.http.get<number>(this.url + '/' + masterId + '/orders/' + orderId + '/salary')
      .subscribe(salary => this.salary = salary);
  }
}
