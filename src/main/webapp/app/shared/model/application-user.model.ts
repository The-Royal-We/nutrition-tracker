import { IUser } from 'app/shared/model/user.model';

export interface IApplicationUser {
  id?: number;
  someData?: string | null;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IApplicationUser> = {};
