import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { User2FormService, User2FormGroup } from './user-2-form.service';
import { IUser2 } from '../user-2.model';
import { User2Service } from '../service/user-2.service';
import { IRole2 } from 'app/entities/role-2/role-2.model';
import { Role2Service } from 'app/entities/role-2/service/role-2.service';

@Component({
  selector: 'jhi-user-2-update',
  templateUrl: './user-2-update.component.html',
})
export class User2UpdateComponent implements OnInit {
  isSaving = false;
  user2: IUser2 | null = null;

  role2sSharedCollection: IRole2[] = [];

  editForm: User2FormGroup = this.user2FormService.createUser2FormGroup();

  constructor(
    protected user2Service: User2Service,
    protected user2FormService: User2FormService,
    protected role2Service: Role2Service,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRole2 = (o1: IRole2 | null, o2: IRole2 | null): boolean => this.role2Service.compareRole2(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ user2 }) => {
      this.user2 = user2;
      if (user2) {
        this.updateForm(user2);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const user2 = this.user2FormService.getUser2(this.editForm);
    if (user2.id !== null) {
      this.subscribeToSaveResponse(this.user2Service.update(user2));
    } else {
      this.subscribeToSaveResponse(this.user2Service.create(user2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUser2>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(user2: IUser2): void {
    this.user2 = user2;
    this.user2FormService.resetForm(this.editForm, user2);

    this.role2sSharedCollection = this.role2Service.addRole2ToCollectionIfMissing<IRole2>(
      this.role2sSharedCollection,
      ...(user2.role2s ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.role2Service
      .query()
      .pipe(map((res: HttpResponse<IRole2[]>) => res.body ?? []))
      .pipe(map((role2s: IRole2[]) => this.role2Service.addRole2ToCollectionIfMissing<IRole2>(role2s, ...(this.user2?.role2s ?? []))))
      .subscribe((role2s: IRole2[]) => (this.role2sSharedCollection = role2s));
  }
}
