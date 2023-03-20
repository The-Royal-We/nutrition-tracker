import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './nutritional-data.reducer';

export const NutritionalDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const nutritionalDataEntity = useAppSelector(state => state.nutritionalData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="nutritionalDataDetailsHeading">
          <Translate contentKey="nutritionTrackerApp.nutritionalData.detail.title">NutritionalData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.id}</dd>
          <dt>
            <span id="weight">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.weight}</dd>
          <dt>
            <span id="steps">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.steps">Steps</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.steps}</dd>
          <dt>
            <span id="sleep">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.sleep">Sleep</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.sleep}</dd>
          <dt>
            <span id="water">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.water">Water</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.water}</dd>
          <dt>
            <span id="protein">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.protein">Protein</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.protein}</dd>
          <dt>
            <span id="carbs">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.carbs">Carbs</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.carbs}</dd>
          <dt>
            <span id="fat">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.fat">Fat</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.fat}</dd>
          <dt>
            <span id="calories">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.calories">Calories</Translate>
            </span>
          </dt>
          <dd>{nutritionalDataEntity.calories}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="nutritionTrackerApp.nutritionalData.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {nutritionalDataEntity.date ? (
              <TextFormat value={nutritionalDataEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="nutritionTrackerApp.nutritionalData.nutritionalWeek">Nutritional Week</Translate>
          </dt>
          <dd>{nutritionalDataEntity.nutritionalWeek ? nutritionalDataEntity.nutritionalWeek.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/nutritional-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/nutritional-data/${nutritionalDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NutritionalDataDetail;
