import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrivilege2 } from '../privilege-2.model';

@Component({
  selector: 'jhi-privilege-2-detail',
  templateUrl: './privilege-2-detail.component.html',
})
export class Privilege2DetailComponent implements OnInit {
  privilege2: IPrivilege2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ privilege2 }) => {
      this.privilege2 = privilege2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
