import {MonoTypeOperatorFunction} from "rxjs";
import {filter} from "rxjs/operators";

export function valueExists<T>(thisArg?: any): MonoTypeOperatorFunction<T> {
  return filter((v: any) => v === false ? true : Boolean(v), thisArg);
}
