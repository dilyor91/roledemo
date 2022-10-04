import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUser2 } from '../user-2.model';

@Component({
  selector: 'jhi-user-2-detail',
  templateUrl: './user-2-detail.component.html',
})
export class User2DetailComponent implements OnInit {
  user2: IUser2 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user2 }) => {
      this.user2 = user2;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
