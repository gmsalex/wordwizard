import {MatDialog, MatDialogRef} from "@angular/material";
import {HttpRequest, HttpResponse} from "@angular/common/http";
import {ProgressDialog} from "../dialog/progress.dialog";
import {Injectable} from "@angular/core";

@Injectable()
export class ProgressService {
  private readonly ignoreForUrls: string[] = [];
  private counter = 0;
  private dialogRef: MatDialogRef<ProgressDialog> = null;

  constructor(private _dialog: MatDialog) {
  }

  show(req: HttpRequest<any>) {
    if (this.ignoreForUrls.includes(req.url)) {
      return;
    }
    this.counter++;
    if (this.dialogRef == null) {
      setTimeout(() => {
        this.dialogRef = this._dialog.open(ProgressDialog, {'minWidth': 200, 'minHeight': 250})
      }, 0);
    }
  }

  hide(res: HttpResponse<any>) {
    if (this.ignoreForUrls.includes(res.url)) {
      return;
    }
    this.counter--;
    if (this.counter == 0 && this.dialogRef != null) {
      this.dialogRef.close();
      this.dialogRef = null;
    }
  }
}
