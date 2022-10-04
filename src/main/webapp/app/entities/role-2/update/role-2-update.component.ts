import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { Role2FormService, Role2FormGroup } from './role-2-form.service';
import { IRole2 } from '../role-2.model';
import { Role2Service } from '../service/role-2.service';

@Component({
  selector: 'jhi-role-2-update',
  templateUrl: './role-2-update.component.html',
})
export class Role2UpdateComponent implements OnInit {
  isSaving = false;
  role2: IRole2 | null = null;

  editForm: Role2FormGroup = this.role2FormService.createRole2FormGroup();

  constructor(
    protected role2Service: Role2Service,
    protected role2FormService: Role2FormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ role2 }) => {
      this.role2 = role2;
      if (role2) {
        this.updateForm(role2);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const role2 = this.role2FormService.getRole2(this.editForm);
    if (role2.id !== null) {
      this.subscribeToSaveResponse(this.role2Service.update(role2));
    } else {
      this.subscribeToSaveResponse(this.role2Service.create(role2));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRole2>>): void {
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

  protected updateForm(role2: IRole2): void {
    this.role2 = role2;
    this.role2FormService.resetForm(this.editForm, role2);
  }
}
