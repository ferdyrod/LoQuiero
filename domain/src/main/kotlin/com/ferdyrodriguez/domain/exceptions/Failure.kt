/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ferdyrodriguez.domain.exceptions

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val errorMessage: String?) {
    class NetworkConnection: Failure(null)
    class ServerError(message: String? = null): Failure(message)
    class LocalError(message: String? = null): Failure(message)

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(message: String?  = null): Failure(message)
}
