import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nutritional-week.reducer';

export const NutritionalWeekDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nutritionalWeekEntity = useAppSelector(state => state.nutritionalWeek.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nutritionalWeekDetailsHeading">
          <Translate contentKey="nutritionTrackerApp.nutritionalWeek.detail.title">NutritionalWeek</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nutritionalWeekEntity.id}</dd>
          <dt>
            <span id="dateFrom">
              <Translate contentKey="nutritionTrackerApp.nutritionalWeek.dateFrom">Date From</Translate>
            </span>
          </dt>
          <dd>
            {nutritionalWeekEntity.dateFrom ? (
              <TextFormat value={nutritionalWeekEntity.dateFrom} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTo">
              <Translate contentKey="nutritionTrackerApp.nutritionalWeek.dateTo">Date To</Translate>
            </span>
          </dt>
          <dd>
            {nutritionalWeekEntity.dateTo ? (
              <TextFormat value={nutritionalWeekEntity.dateTo} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="name">
              <Translate contentKey="nutritionTrackerApp.nutritionalWeek.name">Name</Translate>
            </span>
          </dt>
          <dd>{nutritionalWeekEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/nutritional-week" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nutritional-week/${nutritionalWeekEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NutritionalWeekDetail;
