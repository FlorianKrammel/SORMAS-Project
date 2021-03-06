/*******************************************************************************
 * SORMAS® - Surveillance Outbreak Response Management & Analysis System
 * Copyright © 2016-2018 Helmholtz-Zentrum für Infektionsforschung GmbH (HZI)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 *******************************************************************************/
package de.symeda.sormas.ui.samples;

import static de.symeda.sormas.ui.utils.CssStyles.H3;
import static de.symeda.sormas.ui.utils.LayoutUtil.loc;

import com.vaadin.ui.Label;
import com.vaadin.v7.ui.ComboBox;

import de.symeda.sormas.api.FacadeProvider;
import de.symeda.sormas.api.i18n.I18nProperties;
import de.symeda.sormas.api.i18n.Strings;
import de.symeda.sormas.api.sample.PathogenTestResultType;
import de.symeda.sormas.api.sample.SampleDto;
import de.symeda.sormas.api.utils.fieldaccess.UiFieldAccessCheckers;

public class SampleEditForm extends AbstractSampleForm {

	private static final long serialVersionUID = 1L;

	private static final String LABORATORY_SAMPLE_HEADING_LOC = "laboratorySampleHeadingLoc";

	private static final String HTML_LAYOUT = loc(LABORATORY_SAMPLE_HEADING_LOC) + SAMPLE_COMMON_HTML_LAYOUT;

	public SampleEditForm(boolean isPseudonymized) {
		super(
			SampleDto.class,
			SampleDto.I18N_PREFIX,
			UiFieldAccessCheckers.forSensitiveData(isPseudonymized));
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void addFields() {
		Label laboratorySampleHeadingLabel = new Label(I18nProperties.getString(Strings.headingLaboratorySample));
		laboratorySampleHeadingLabel.addStyleName(H3);
		getContent().addComponent(laboratorySampleHeadingLabel, LABORATORY_SAMPLE_HEADING_LOC);

		addCommonFields();

		initializeRequestedTestFields();

		addValidators();

		initializeAccessAndAllowedAccesses();

		setVisibilities();

		addValueChangeListener(e -> {
			defaultValueChangeListener();
			if (FacadeProvider.getPathogenTestFacade().hasPathogenTest(getValue().toReference())) {
				getField(SampleDto.PATHOGEN_TEST_RESULT).setRequired(true);
			} else {
				getField(SampleDto.PATHOGEN_TEST_RESULT).setEnabled(false);
			}
		});
	}

	public void makePathogenTestResultRequired() {
		ComboBox pathogenTestResultField = (ComboBox) getFieldGroup().getField(SampleDto.PATHOGEN_TEST_RESULT);
		pathogenTestResultField.setEnabled(true);
		pathogenTestResultField.setRequired(true);

		if (pathogenTestResultField.getValue() == null) {
			pathogenTestResultField.setValue(PathogenTestResultType.PENDING);
		}
	}

	@Override
	protected String createHtmlLayout() {
		return HTML_LAYOUT;
	}
}
