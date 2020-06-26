/**
 * Copyright (c) 2018-2028, Chill Zhuang (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snackpub.core.cloud.hystrix;

import com.snackpub.core.secure.BladeUser;
import com.snackpub.core.secure.utils.SecureUtil;
import com.snackpub.core.tools.utils.Charsets;
import com.snackpub.core.tools.utils.UrlUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息获取器
 *
 * @author Chill
 */
public class BladeAccountGetter implements BladeHystrixAccountGetter {

    @Override
    public String get(HttpServletRequest request) {
        BladeUser account = SecureUtil.getUser();
        if (account == null) {
            return null;
        }
        // 增加用户头 123[admin]
        String xAccount = String.format("%s[%s]", account.getUserId(), account.getUserName());
        return UrlUtil.encodeURL(xAccount, Charsets.UTF_8);
    }

}
