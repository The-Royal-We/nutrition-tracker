import dayjs from 'dayjs';
import { INutritionalData } from 'app/shared/model/nutritional-data.model';

export interface INutritionalWeek {
  id?: number;
  dateFrom?: string | null;
  dateTo?: string | null;
  name?: string | null;
  nutritionalData?: INutritionalData[] | null;
}

export const defaultValue: Readonly<INutritionalWeek> = {};
