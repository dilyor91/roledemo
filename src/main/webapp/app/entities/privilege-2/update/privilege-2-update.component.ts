import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { Privilege2FormService, Privilege2FormGroup } from './privilege-2-form.service';
import { IPrivilege2 } from '../privilege-2.model';
import { Privilege2Service } from '../service/privilege-2.service';
import { IRole2 } from 'app/entities/role-2/role-2.model';
import { Role2Service } from 'app/entities/role-2/service/role-2.service';

@Component({
  selector: 'jhi-privilege-2-update',
  templateUrl: './privilege-2-update.component.html',
})
export class Privilege2UpdateComponent implements OnInit {
  isSaving = false;
  privilege2: IPrivilege2 | null = null;

  role2sSharedCollection: IRole2[] = [];

  editForm: Privilege2FormGroup = this.privilege2FormService.createPrivilege2FormGroup();

  constructor(
    protected privilege2Service: Privilege2Service,
    protected privilege2FormService: Privilege2FormService,
    protected role2Service: Role2Service,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRole2 = (o1: IRole2 | null, o2: IRole2 | null): boolean => this.role2Service.compareRole2(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ privilege2 }) => {
      this.privilege2 = privilege2;
      if (privilege2) {
        this.updateForm(privilege2);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const privilege2 = this.privilege2FormService.getPrivilege2(this.editForm);
    if (privilege2.id !== null) {
      this.subscribeToSaveResponse(this.privilege2Service.update(privilege2));
    } else {
      this.subscribeToSaveResponse(this.privilege2Service.create(privilege2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrivilege2>>): void {
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

  protected updateForm(privilege2: IPrivilege2): void {
    this.privilege2 = privilege2;
    this.privilege2FormService.resetForm(this.editForm, privilege2);

    this.role2sSharedCollection = this.role2Service.addRole2ToCollectionIfMissing<IRole2>(
      this.role2sSharedCollection,
      ...(privilege2.role2s ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.role2Service
      .query()
      .pipe(map((res: HttpResponse<IRole2[]>) => res.body ?? []))
      .pipe(map((role2s: IRole2[]) => this.role2Service.addRole2ToCollectionIfMissing<IRole2>(role2s, ...(this.privilege2?.role2s ?? []))))
      .subscribe((role2s: IRole2[]) => (this.role2sSharedCollection = role2s));
  }
}
