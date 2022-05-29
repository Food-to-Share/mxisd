/*
 * mxisd - Matrix Identity Server Daemon
 * Copyright (C) 2018 Kamax Sarl
 *
 * https://www.kamax.io/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.kamax.mxisd.threepid.connector.niss;

import com.google.gson.JsonObject;
import io.kamax.matrix.json.GsonUtil;
import io.kamax.mxisd.Mxisd;
import io.kamax.mxisd.config.threepid.connector.NissTwilioConfig;
import io.kamax.mxisd.config.threepid.medium.NissConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class BuiltInNissConnectorSupplier implements NissConnectorSupplier {

    @Override
    public Optional<NissConnector> apply(NissConfig cfg, Mxisd mxisd) {
        if (StringUtils.equals(BlackholeNissConnector.ID, cfg.getConnector())) {
            return Optional.of(new BlackholeNissConnector());
        }

        if (StringUtils.equals(NissSmsTwilioConnector.ID, cfg.getConnector())) {
            NissTwilioConfig cCfg = GsonUtil.get().fromJson(cfg.getConnectors().getOrDefault(NissSmsTwilioConnector.ID, new JsonObject()), NissTwilioConfig.class);
            return Optional.of(new NissSmsTwilioConnector(cCfg));
        }

        return Optional.empty();
    }

}
