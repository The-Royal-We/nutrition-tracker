import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INutritionalWeek } from 'app/shared/model/nutritional-week.model';
import { getEntities as getNutritionalWeeks } from 'app/entities/nutritional-week/nutritional-week.reducer';
import { INutritionalData } from 'app/shared/model/nutritional-data.model';
import { getEntity, updateEntity, createEntity, reset } from './nutritional-data.reducer';

export const NutritionalDataUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const nutritionalWeeks = useAppSelector(state => state.nutritionalWeek.entities);
  const nutritionalDataEntity = useAppSelector(state => state.nutritionalData.entity);
  const loading = useAppSelector(state => state.nutritionalData.loading);
  const updating = useAppSelector(state => state.nutritionalData.updating);
  const updateSuccess = useAppSelector(state => state.nutritionalData.updateSuccess);

  const handleClose = () => {
    navigate('/nutritional-data');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getNutritionalWeeks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...nutritionalDataEntity,
      ...values,
      nutritionalWeek: nutritionalWeeks.find(it => it.id.toString() === values.nutritionalWeek.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...nutritionalDataEntity,
          nutritionalWeek: nutritionalDataEntity?.nutritionalWeek?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="nutritionTrackerApp.nutritionalData.home.createOrEditLabel" data-cy="NutritionalDataCreateUpdateHeading">
            <Translate contentKey="nutritionTrackerApp.nutritionalData.home.createOrEditLabel">Create or edit a NutritionalData</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="nutritional-data-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.weight')}
                id="nutritional-data-weight"
                name="weight"
                data-cy="weight"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.steps')}
                id="nutritional-data-steps"
                name="steps"
                data-cy="steps"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.sleep')}
                id="nutritional-data-sleep"
                name="sleep"
                data-cy="sleep"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.water')}
                id="nutritional-data-water"
                name="water"
                data-cy="water"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.protein')}
                id="nutritional-data-protein"
                name="protein"
                data-cy="protein"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.carbs')}
                id="nutritional-data-carbs"
                name="carbs"
                data-cy="carbs"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.fat')}
                id="nutritional-data-fat"
                name="fat"
                data-cy="fat"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.calories')}
                id="nutritional-data-calories"
                name="calories"
                data-cy="calories"
                type="text"
              />
              <ValidatedField
                label={translate('nutritionTrackerApp.nutritionalData.date')}
                id="nutritional-data-date"
                name="date"
                data-cy="date"
                type="date"
              />
              <ValidatedField
                id="nutritional-data-nutritionalWeek"
                name="nutritionalWeek"
                data-cy="nutritionalWeek"
                label={translate('nutritionTrackerApp.nutritionalData.nutritionalWeek')}
                type="select"
              >
                <option value="" key="0" />
                {nutritionalWeeks
                  ? nutritionalWeeks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/nutritional-data" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NutritionalDataUpdate;
