import { GradeCompany } from './../app/model/gradeCompany';
import { Company } from './../app/model/company';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {UpdateUser} from "../app/model/udate-user";
import {User} from "../app/model/user";
import {UpdateCompany} from "../app/model/company-update";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  url = "http://localhost:8083/api/company";
  urlUpdate = "http://localhost:8083/company/update";

  constructor(private http: HttpClient) { }

  save(newComoany:Company):Observable<Company>{
    return this.http.post<Company>(this.url,newComoany);
  }
  grade(gradeCompany:GradeCompany):Observable<Company>{
    return this.http.post<Company>(this.url+"/grade",gradeCompany);
  }
  salary(salaryCompany:GradeCompany):Observable<Company>{
    return this.http.post<Company>(this.url+"/salary",salaryCompany);
  }
  getOneByOwnerId(id:number):Observable<Company>{
    return this.http.get<Company>(`${this.url+"/owner"}/${id}`);
  }


  updateCompany(company:UpdateCompany):Observable<Company> {
    return this.http.put<Company>(this.url + "/update", company);
  }

  findByName(name:string):Observable<Company>{
    const params:HttpParams=new HttpParams().append('name',name);
    return this.http.get<Company>(this.url,{params});
  }
  getOneById(id:number):Observable<Company>{
    return this.http.get<Company>(`${this.url}/${id}`);
  }
}
