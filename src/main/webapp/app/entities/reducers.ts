import nutritionalData from 'app/entities/nutritional-data/nutritional-data.reducer';
import nutritionalWeek from 'app/entities/nutritional-week/nutritional-week.reducer';
import applicationUser from 'app/entities/application-user/application-user.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  nutritionalData,
  nutritionalWeek,
  applicationUser,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
