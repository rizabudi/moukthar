FROM php:8.1-cli

# Install necessary dependencies
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    zip \
    && rm -rf /var/lib/apt/lists/*
    
# Install and enable pdo_mysql
RUN docker-php-ext-install pdo_mysql && docker-php-ext-enable pdo_mysql

# Copy the wait-for-it script
COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh

# Copy Composer binary
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Set working directory
WORKDIR /var/www/html

# Copy application files
COPY . /var/www/html/

# Set logging to stdout/stderr
RUN echo "error_log = /proc/self/fd/2" >> /usr/local/etc/php/conf.d/docker-php-logging.ini

# Install Composer dependencies
RUN composer install --no-dev --optimize-autoloader

# Set permissions
RUN chown -R www-data:www-data /var/www/html && chmod -R 755 /var/www/html

# Run the WebSocket server
CMD ["bash", "-c", "wait-for-it.sh mysql:3306 -- php App.php"]
