import dayjs from 'dayjs';
import { INutritionalWeek } from 'app/shared/model/nutritional-week.model';

export interface INutritionalData {
  id?: number;
  weight?: number | null;
  steps?: number | null;
  sleep?: number | null;
  water?: number | null;
  protein?: number | null;
  carbs?: number | null;
  fat?: number | null;
  calories?: number | null;
  date?: string | null;
  nutritionalWeek?: INutritionalWeek | null;
}

export const defaultValue: Readonly<INutritionalData> = {};
