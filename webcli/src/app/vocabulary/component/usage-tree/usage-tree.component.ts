import {Component} from '@angular/core';
import {UsageDatabase} from '../../service/usage/usage-database.service';

@Component({
  selector: 'usage-tree',
  templateUrl: './usage-tree.component.html',
  styleUrls: ['./usage-tree.component.scss']
})
export class UsageTreeComponent {
  selectedText: string;

  constructor(public usageDb: UsageDatabase) {
  }

  onTextSelection(text) {
    this.selectedText = text;
  }
}
