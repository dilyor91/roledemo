import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRole2 } from '../role-2.model';

@Component({
  selector: 'jhi-role-2-detail',
  templateUrl: './role-2-detail.component.html',
})
export class Role2DetailComponent implements OnInit {
  role2: IRole2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ role2 }) => {
      this.role2 = role2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
