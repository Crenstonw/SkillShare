import { Component, OnInit, TemplateRef, inject } from '@angular/core';
import { TagService } from '../../services/tag.service';
import { Tag } from '../../models/orders.model';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  styleUrl: './tags.component.css'
})
export class TagsComponent implements OnInit {
  idTag: string = '';
  tags: Tag[] = [];
  newTagForm!: FormGroup;
  private modalService = inject(NgbModal);
  closeResult = '';

  constructor(private tagService: TagService) { }

  initForm(name?: string) {
    this.newTagForm = new FormGroup({
      name: new FormControl(name || '')
    });
  }

  tagModal(content: TemplateRef<any>, name?: string, id?: string) {
    if (id) {
      this.idTag = id;
    }
    this.initForm(name);
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', size: 'xl' }).result.then(
      (result) => {
        this.closeResult = `Closed with: ${result}`;
      },
      (reason) => {
        this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
      },
    );
  }

  private getDismissReason(reason: any): string {
    switch (reason) {
      case ModalDismissReasons.ESC:
        return 'by pressing ESC';
      case ModalDismissReasons.BACKDROP_CLICK:
        return 'by clicking on a backdrop';
      default:
        return `with: ${reason}`;
    }
  }

  ngOnInit(): void {
    this.getAllTags();
  }

  getAllTags() {
    this.tagService.GetTags().subscribe(p => {
      this.tags = p.content;
    });
  }

  postTag() {
    this.tagService.PostTag(this.newTagForm.value.name).subscribe(p => {
      window.location.reload();
    });
  }

  putTag() {
    this.tagService.EditTag(this.idTag, this.newTagForm.value.name).subscribe(p => {
      window.location.reload();
    });
  }

  deleteTag() {
    this.tagService.DeleteTag(this.idTag).subscribe(p => {
      window.location.reload();
    });
    this.modalService.dismissAll();
  }
}
